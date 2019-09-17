#!/bin/bash -ex

mkdir -p .m2/repository

mvn -Dmaven.repo.local=.m2/repository

mvn package -Dmaven.repo.local=.m2/repository

cp target/*.jar ../artifacts