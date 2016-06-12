#!/usr/bin/env bash

#
# 此脚本用来生成基于sbt的多工程项目,只是最基本的,生成后依赖库等还是要额外处理
# ./**.sh projectname moduleone moduletwo modulethree ...
#

wfile() {
	echo -e "organization := \"com.bob\"\n" >> $2
	echo -e "name := \"$1\"\n" >> $2
	echo -e "version := \"0.0.1-SNAPSHOT\"\n" >> $2
	echo -e "scalaVersion := \"2.11.6\"\n" >> $2
}

if [ $# == 0 ]
then
	echo "至少输入一个参数，这个参数将被当作工程名"
	exit -1
fi

echo "你输入的参数如下: $*"

rm -rf $1
mkdir $1
cd $1
touch build.sbt
wfile $1 $(pwd)/build.sbt
mkdir -p src/main/scala
mkdir project
cd project
buildFileName=Build.scala
touch $buildFileName
cd ..

if [ $# -gt 1 ]
then
	for cur in $*
	do
		if [ $cur = $1 ]
		then
			continue
    	fi
		mkdir $cur
		cd $cur
		touch build.sbt
		wfile $cur $(pwd)/build.sbt
		mkdir -p src/main/scala
		cd ..
	done
fi

if [ $# -gt 1 ]
then
	cd project
	for cur in $*
	do
		if [ $cur = $1 ]
		then
			echo "import sbt._" >> $buildFileName
			echo -e "import Keys._\n" >> $buildFileName
			echo "object ProjectBuild extends Build { " >> $buildFileName
			params=$*
			firstparam=$1
			rp=${params// /","}
			replaceparam=${rp#$firstparam,}
			echo "  lazy val root = (project in file(\".\")).aggregate($replaceparam)" >> $buildFileName
		else
			echo "  lazy val $cur = project in file(\"$cur\")" >> $buildFileName
		fi
	done
	echo "}" >> $buildFileName
fi