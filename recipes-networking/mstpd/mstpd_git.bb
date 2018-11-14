#
SUMMARY = "Multiple Spanning Tree Protocol Daemon"

PR = "tano11"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4325afd396febcb659c36b49533135d4 \
                    file://debian/copyright;md5=332234a99007d25da40f41ee96aa388f"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}/patches:${THISDIR}/${PN}/files:"

SRC_URI = "\
	git://github.com/mstpd/mstpd.git;protocol=https;branch=master \
"

PV = "0.0.7+git${SRCPV}"

# 24.08.2018 main.c: free file pointer when exiting
SRCREV = "e4f0ba5a48649a3253f8b353c87c965e12aafc50"

SRC_URI += "\
	file://bridge-stp \
	file://mstpd.config \
	file://mstpd.sh \
	file://mstpd.init \
"

S = "${WORKDIR}/git"

inherit autotools

EXTRA_OECONF = "--sbindir=/sbin"

do_install_append() {
	if [ "${@bb.utils.contains('DISTRO_FEATURES', 'procd', 'true', 'false', d)}" = "true" ]; then

		# Remove unused in OpenWrt files
		rm -rf ${D}${sysconfdir}/network
		rm -rf ${D}${sysconfdir}/bridge-stp.conf
		rm -rf ${D}/usr/libexec/mstpctl-utils/*

		# Install directories
		install -dm 0755 ${D}${sysconfdir}/config
		install -dm 0755 ${D}${sysconfdir}/init.d
		install -dm 0755 ${D}/lib/functions
		install -dm 0755 ${D}/sbin

		# Install files
		install -m 0755 ${WORKDIR}/bridge-stp ${D}/sbin/bridge-stp
		install -m 0644 ${WORKDIR}/mstpd.config ${D}${sysconfdir}/config/mstpd
		install -m 0755 ${WORKDIR}/mstpd.init ${D}${sysconfdir}/init.d/mstpd
		install -m 0755 ${WORKDIR}/mstpd.sh ${D}/lib/functions/mstpd.sh
	fi
}

FILES_${PN} += "/lib/functions/"

inherit openwrt-services

OPENWRT_SERVICE_PACKAGES = "mstpd"
OPENWRT_SERVICE_SCRIPTS_mstpd += "mstpd"
OPENWRT_SERVICE_STATE_mstpd-mstpd ?= "enabled"

CONFFILES_${PN}_append = "\
	${sysconfdir}/config/mstpd \
"
