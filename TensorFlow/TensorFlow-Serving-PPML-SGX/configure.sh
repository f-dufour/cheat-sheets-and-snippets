#!/bin/bash
# Florent Dufour
# Nov. 2022

apt-get update
apt-get upgrade -y
apt-get install -y fish tree git python3-dev python3-pip htop tmux git
curl https://get.docker.com/ | bash

#########################
## Client Training GPU ##
#########################

## ----------- ##
## PREPARATION ##
## ----------- ##

# Get resources
mkdir -p git; cd $_
git clone --depth=1 https://github.com/intel/confidential-computing-zoo.git
cd confidential-computing-zoo/cczoo/tensorflow-serving-cluster/tensorflow-serving/docker/client

## -------------- ##
## MODEL TRAINING ##
## -------------- ##

# Model training would happen here
# Let's save time and download ResNet50 model with FP32 precision
./download_model.sh
pip3 install -r requirements.txt
python3 ./model_graph_to_saved_model.py --import_path `pwd -P`/models/resnet50-v15-fp32/resnet50-v15-fp32.pb --export_dir `pwd -P`/models/resnet50-v15-fp32 --model_version 1 --inputs input --outputs predict

## ---------- ##
## ENCRYPTION ##
## ---------- ##

# Create SSL certificates
service_domain_name=grpc.tf-serving.service.com
./generate_oneway_ssl_config.sh ${service_domain_name}
tar -cvf ssl_configure.tar ssl_configure

# Create Encrypted Model File
mkdir plaintext/
mv models/resnet50-v15-fp32/1/saved_model.pb plaintext/
LD_LIBRARY_PATH=./libs ./gramine-sgx-pf-crypt encrypt -w files/wrap-key -i plaintext/saved_model.pb -o  models/resnet50-v15-fp32/1/saved_model.pb
tar -cvf models.tar models

# Secret Provisioning
cd ../secret_prov
AZURE=1 ./build_secret_prov_image.sh
./run_secret_prov.sh -i secret_prov_server:latest

############
## Server ##
############

cd ../tf-serving
cp ../client/models.tar .
cp ../client/ssl_configure.tar .
tar -xvf models.tar
tar -xvf ssl_configure.tar

# Get resources
mkdir -p git; cd $_
git clone --depth=1 https://github.com/intel/confidential-computing-zoo.git
cd confidential-computing-zoo/cczoo/tensorflow-serving-cluster/tensorflow-serving/

# Setup SGX
sudo ./setup_azure_vm.sh
sudo systemctl status aesmd

## ---------------------------------- ##
## GET MODEL AND KEYS from client GPU ##
## ---------------------------------- ##

cd docker/tf_serving

# scp files from client training GPU here
cp /tmp/models.tar .
cp /tmp/ssl_configure.tar .
tar -xvf models.tar
tar -xvf ssl_configure.tar

# Tensorflow serving
AZURE=1 ./build_gramine_tf_serving_image.sh
cp ssl_configure/ssl.cfg .
./run_gramine_tf_serving.sh -i gramine_tf_serving:latest -p 8500-8501 -m resnet50-v15-fp32 -s ssl.cfg -a attestation.service.com:$ATTESTATION_IP