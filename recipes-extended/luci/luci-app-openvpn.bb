#
# LuCI Support for OpenVPN
#
# This file Copyright (c) 2018, Tano Systems. All Rights Reserved.
# Anton Kikin <a.kikin@tano-systems.com>
#
PR = "tano6"

SUMMARY = "LuCI Support for OpenVPN"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

RDEPENDS_${PN} += "openvpn luci-compat"

inherit openwrt-luci-app
inherit openwrt-luci-i18n
inherit luasrcdiet

SRC_URI = "${LUCI_GIT_URI};branch=${LUCI_GIT_BRANCH};protocol=${LUCI_GIT_PROTOCOL};subpath=applications/luci-app-openvpn;destsuffix=git/"
SRCREV = "${LUCI_GIT_SRCREV}"
S = "${WORKDIR}/git"
