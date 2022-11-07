#!/bin/bash

apt-get update
apt-get upgrade -y
apt-get install -y fish tree git python3-dev python3-pip htop tmux git
curl https://get.docker.com/ | bash