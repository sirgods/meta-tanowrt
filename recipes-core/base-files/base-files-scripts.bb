# Copyright (C) 2018 Daniel Dickinson <cshored@thecshore.com>
# Copyright (C) 2018-2019 Anton Kikin <a.kikin@tano-systems.com>
# Released under the MIT license.  See COPYING.MIT for terms

inherit openwrt openwrt-base-files

PR = "tano1"

DESCRIPTION = "Subpackages from base-files from OpenWrt core"
HOMEPAGE = "http://wiki.openwrt.org/"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=94d55d512a9ba36caa9b7df079bae19f"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:${THISDIR}/${PN}/patches:${THISDIR}/${PN}/files:"

SRC_URI += "git://github.com/openwrt/openwrt.git;protocol=git;branch=${OPENWRT_BRANCH} \
           "

SRCREV = "${OPENWRT_SRCREV}"

S = "${WORKDIR}/git"
SC = "${WORKDIR}/git/package/base-files/files"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

PACKAGES = "\
	${PN}-openwrt \
	${PN}-sysupgrade \
"

# sysupgrade
FILESEXTRAPATHS_prepend := ""
SRC_URI += "file://files-sysupgrade/*"

do_install_append () {
	install -dm 0755 ${D}${sysconfdir}/config

	install -Dm 0644 ${SC}/lib/functions.sh ${D}/lib/functions.sh
	install -Dm 0644 ${SC}/lib/functions/uci-defaults.sh ${D}/lib/functions/uci-defaults.sh
	install -Dm 0644 ${SC}/lib/functions/system.sh ${D}/lib/functions/system.sh
	install -Dm 0755 ${SC}/bin/ipcalc.sh ${D}/bin/ipcalc.sh

	# sysupgrade
	install -Dm 0644 ${WORKDIR}/files-sysupgrade/etc/sysupgrade.conf ${D}/etc/sysupgrade.conf
	install -Dm 0755 ${WORKDIR}/files-sysupgrade/sbin/sysupgrade ${D}/sbin/sysupgrade
	install -Dm 0755 ${WORKDIR}/files-sysupgrade/sbin/firstboot ${D}/sbin/firstboot

	install -dm 0755 ${D}/lib/upgrade
	install -m 0755 ${WORKDIR}/files-sysupgrade/lib/upgrade/stage2 ${D}/lib/upgrade
	install -m 0644 ${WORKDIR}/files-sysupgrade/lib/upgrade/common.sh ${D}/lib/upgrade
	install -m 0644 ${WORKDIR}/files-sysupgrade/lib/upgrade/fwtool.sh ${D}/lib/upgrade
	install -m 0644 ${WORKDIR}/files-sysupgrade/lib/upgrade/nand.sh ${D}/lib/upgrade

	install -dm 0755 ${D}/lib/upgrade/keep.d
	install -m 0644 ${WORKDIR}/files-sysupgrade/lib/upgrade/keep.d/base-files-essential ${D}/lib/upgrade/keep.d
}

FILES_${PN}-openwrt = "\
                      /lib/functions.sh \
                      /lib/functions/uci-defaults.sh \
                      /lib/functions/system.sh \
                      /bin/ipcalc.sh \
                      ${sysconfdir}/config \
                      "

FILES_${PN}-sysupgrade = "\
                         /etc/sysupgrade.conf \
                         /sbin/sysupgrade \
                         /lib/upgrade/* \
                         /sbin/firstboot \
                         "

CONFFILES_${PN}-openwrt += "\
                           ${sysconfdir}/config \
                           "

CONFFILES_${PN}-sysupgrade += "\
                              ${sysconfdir]/sysupgrade.conf \
                              "

PACKAGE_ARCH = "${MACHINE_ARCH}"

BBCLASSEXTEND = "native"
