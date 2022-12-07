readcopyright() {
  for file in `ls $1`
  do
    fullfile=$1"/"$file
    if [ -d $fullfile -a $fullfile != "./build" ]
    then    
       readcopyright $fullfile
    else
        line2=`sed -n '2p' $fullfile`
        line23=`sed -n '23p' $fullfile`
        line24=`sed -n '24p' $fullfile`
        if [[ ( $line2 =~ "Copyright" ) && ( $line23 =~ "questions" ) ]]
        then
          sed -i '3,23d' $fullfile
        fi
    fi
  done
}

for file in `ls .`
  do
    if [ -d $file -a $file != "build" ]
    then
       echo "remove copyright $file"
       readcopyright $file
    fi
  done
