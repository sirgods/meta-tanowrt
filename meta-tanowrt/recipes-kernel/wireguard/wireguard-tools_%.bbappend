PR_append = ".tano3"
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}/patches:${THISDIR}/${PN}/files:"

SRC_URI += "\
	file://wireguard_watchdog \
	file://wireguard.sh \
"

do_configure[depends] += "virtual/kernel:do_shared_workdir"

do_install_append () {
	install -dm 0755 ${D}${bindir}
	install -m 0755 ${WORKDIR}/wireguard_watchdog ${D}${bindir}/

	install -dm 0755 ${D}${base_libdir}/netifd/proto
	install -m 0755 ${WORKDIR}/wireguard.sh ${D}${base_libdir}/netifd/proto/
}

FILES_${PN} += " \
	${sysconfdir} \
	${bindir} \
	${base_libdir} \
"

DEPENDS += "wireguard-module"
RDEPENDS_${PN} += "ubus jsonpath kernel-module-wireguard"
RDEPENDS_${PN}_remove = "wireguard-module"
