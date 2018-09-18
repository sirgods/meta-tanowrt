#
# Octo's ping library
#
# This file Copyright (C) 2018 Tano Systems
# Anton Kikin <a.kikin@tano-systems.com>
#

SUMMARY = "Octo's ping library"
DESCRIPTION = "liboping is a C library to generate ICMP echo requests, better known as 'ping packets'. It is intended for use in network monitoring applications or applications that would otherwise need to fork ping frequently."
SECTION = "libs"
LICENSE = "LGPL-2.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=4fbd65380cdd255951079008b364516c"
DEPENDS = "ncurses"

PV = "1.9.0"
PR = "tano0"

SRC_URI = "https://noping.cc/files/${PN}-${PV}.tar.gz"

SRC_URI[md5sum] = "28d085b95d1ca1acd541fc2606d5e02d"
SRC_URI[sha256sum] = "86b44f684a3151bd4b5b75336876635ecb0f6cfe54a2fb29a6da06432f2dbb00"

S = "${WORKDIR}/${PN}-${PV}"

inherit autotools

EXTRA_OECONF = "\
	--without-perl-bindings \
	--enable-shared \
	--enable-static \
"

do_install_append() {
	# Remove oping and noping utilities
	rm -rf ${D}${bindir}
	rm -rf ${D}${mandir}/man8
}

LEAD_SONAME = "liboping.so"

FILES_${PN} = "${includedir} ${libdir}"
FILES_${PN}-doc = "${mandir}/man3"