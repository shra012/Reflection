#!/bin/bash -ex

mkdir -p .m2/repository

mvn versions:set -DnewVersion=$(git rev-parse HEAD) -Dmaven.repo.local=.m2/repository

mvn package -Dmaven.repo.local=.m2/repository

cp target/*.jar ../artifacts