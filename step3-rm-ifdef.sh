removeifdef() {
  for file in `ls $1`
  do
    fullfile=$1"/"$file
    if [ -d $fullfile ]
    then    
       removeifdef $fullfile
    else
        rm -rf temp.txt
        unifdef -U_WIN32 -UJAVAW -U_MSC_VER $fullfile 1>temp.txt 2>/dev/null
        if [ $? -eq 1 ]
        then
          rm -rf $fullfile
          mv temp.txt $fullfile
        fi
        rm -rf temp.txt
    fi
  done
}

removeifdefprint() {
  echo "remove ifdef $1"
  removeifdef $1
}

removeifdefprint common/src
removeifdefprint corba/src
removeifdefprint hotspot/src
removeifdefprint jaxp/src
removeifdefprint jaxws/src
removeifdefprint jdk/src
removeifdefprint langtools/src
removeifdefprint nashorn/src
