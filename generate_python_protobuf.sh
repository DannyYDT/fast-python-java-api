#!/bin/sh

for f in protobuf/*.proto; do
    protoc -I ./ --python_out=python/api/protobuf $f
    # Needed because of the way protoc resolves packages... see https://github.com/protocolbuffers/protobuf/issues/1491#issuecomment-438138293
    sed -i -E 's/^import.*_pb2/from . \0/' python/api/protobuf/*.py
done
