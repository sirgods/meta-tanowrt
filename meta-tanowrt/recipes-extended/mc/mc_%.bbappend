PR_append = ".tano1"

do_install_append() {
	install -d ${D}${libexecdir}/mc/extfs.d
	touch ${D}${libexecdir}/mc/extfs.d/.keepdir
}

# Fix "cannot open /usr/libexec/mc/extfs.d directory" warning at mc start
FILES_${PN} += "${libexecdir}/mc/extfs.d/.keepdir"
