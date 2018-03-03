#!/usr/bin/env bash

# command line tools
xcode-select --install

easy_install pip
pip install ansible

ansible-galaxy install -f --ignore-errors -r requirements.yml

ansible-playbook install.yml

brew cask install android-studio

brew cask install intellij-idea-ce