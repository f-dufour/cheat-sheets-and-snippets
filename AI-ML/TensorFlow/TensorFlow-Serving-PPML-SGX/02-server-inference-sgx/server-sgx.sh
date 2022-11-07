#!/bin/bash
# Server SGX

ATTESTATION_IP="138.246.237.19"

## ------------------- ##
## MACHINE PREPARATION ##
## ------------------- ##

# apt stuff
apt-get update
apt-get upgrade -y
apt-get install fish tmux

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