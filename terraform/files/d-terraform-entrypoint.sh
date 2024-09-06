#!/bin/bash
python3 -m venv /opt/ansible
source /opt/ansible/bin/activate
pip install pymongo==3.12.0
pip install ansible==2.9.27

exec "$@"
