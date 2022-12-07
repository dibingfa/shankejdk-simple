chmod 755 configure

function deeprm() {
    for value in $@
    do
        rm -rf $value */$value */*/$value */*/*/$value */*/*/*/$value
    done
}

deeprm test .jcheck
deeprm windows aix bsd aarch64 ppc sparc aix_ppc bsd_x86 bsd_zero linux_aarch64 linux_ppc linux_sparc solaris_sparc windows_x86
