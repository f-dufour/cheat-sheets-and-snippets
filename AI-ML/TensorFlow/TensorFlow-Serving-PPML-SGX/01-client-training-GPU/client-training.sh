#!/bin/bash
# Client Training GPU

TF_SGX_SERVER_IP="20.229.102.204"

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

# /!\ Send Encrypted Model and SSL certificates here
scp models.tar        root@$TF_SGX_SERVER_IP:/tmp
scp ssl_configure.tar root@$TF_SGX_SERVER_IP:/tmp