#!/bin/sh

for f in protobuf/*.proto; do
    protoc -I ./ --python_out=python/api/ $f
done
