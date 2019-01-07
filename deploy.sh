#!/usr/bin/env bash

# generate .pb file for creating google cloud endpoint
java -jar ~/.ivy2/cache/com.github.os72/protoc-jar/jars/protoc-jar-3.6.0.jar -I=./protobuf/src/main/protobuf/ --descriptor_set_out=./protobuf/target/ccm.pb ./protobuf/src/main/protobuf/ccm.proto

#protoc -I=./protobuf/src/main/protobuf/ --descriptor_set_out=./protobuf/target/ccm.pb ./protobuf/src/main/protobuf/ccm.proto
# deploy cloud enpoint
google-cloud-sdk/bin/gcloud endpoints services deploy ./protobuf/target/ccm.pb ./kubernetes/api_config.yaml
# creating kubernetes cluster
google-cloud-sdk/bin/kubectl create -f ./kubernetes/kub_service_deploy.yaml
