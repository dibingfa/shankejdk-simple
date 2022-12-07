## init from adoptopenjdk sources

```shell
wget https://github.com/AdoptOpenJDK/openjdk8-upstream-binaries/releases/download/jdk8u312-b07/OpenJDK8U-sources_8u312b07.tar.gz
tar -zxvf OpenJDK8U-sources_8u312b07.tar.gz
cd openjdk-8u312-b07-sources
```

## execute remove shell

```shell
sh step1-rm-directory.sh
sh step2-rm-copyright.sh
sh step3-rm-ifdef.sh
```

## test make

```shell
make clean
./configure --with-jobs=4 --with-milestone=fcs --with-update-version=312 --with-build-number=b07
make images 
```
