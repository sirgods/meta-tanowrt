#
inherit core-image
inherit tanowrt-image-buildinfo
inherit tanowrt-kmods

inherit rootfs-var-lib-opkg-symlink
inherit rootfs-rm-boot-dir
inherit rootfs-rm-opkg-lists
inherit rootfs-var-in-tmpfs
inherit rootfs-locale
inherit rootfs-misc-fixups
inherit set-root-password

IMAGE_FEATURES += "package-management ssh-server-dropbear"

# Disable runtime dependency on run-postinsts
ROOTFS_BOOTSTRAP_INSTALL = ""

IMAGE_OVERLAY_NAME_SUFFIX = ".overlay"

IMAGE_OVERHEAD_FACTOR = "1"

BAD_RECOMMENDATIONS += "busybox-syslog"
BAD_RECOMMENDATIONS += "busybox-udhcpc"
BAD_RECOMMENDATIONS += "shared-mime-info"

#
# Available IMAGE_FEATURES
#
FEATURE_PACKAGES_cgroup = "packagegroup-tanowrt-cgroup"
FEATURE_PACKAGES_ddns = "packagegroup-tanowrt-ddns"
FEATURE_PACKAGES_ftp = "packagegroup-tanowrt-ftp"
FEATURE_PACKAGES_lldp = "packagegroup-tanowrt-lldp"
FEATURE_PACKAGES_mbusgw = "packagegroup-tanowrt-mbusgw"
FEATURE_PACKAGES_modem = "packagegroup-tanowrt-modem"
FEATURE_PACKAGES_mqtt = "packagegroup-tanowrt-mqtt"
FEATURE_PACKAGES_openvpn = "packagegroup-tanowrt-openvpn"
FEATURE_PACKAGES_perftools = "packagegroup-tanowrt-perftools"
FEATURE_PACKAGES_snmp = "packagegroup-tanowrt-snmp"
FEATURE_PACKAGES_statistics = "packagegroup-tanowrt-statistics"
FEATURE_PACKAGES_stp = "packagegroup-tanowrt-stp"
FEATURE_PACKAGES_watchdog = "packagegroup-tanowrt-watchdog"
FEATURE_PACKAGES_webterminal = "packagegroup-tanowrt-webterminal"
FEATURE_PACKAGES_wifi = "packagegroup-tanowrt-wifi"
FEATURE_PACKAGES_wireguard = "packagegroup-tanowrt-wireguard"
FEATURE_PACKAGES_crypto = "packagegroup-tanowrt-crypto"

#
# Create empty overlay image
#
IMAGE_CMD_ubifs_append() {
	# Do not remove this comment. Original IMAGE_CMD_ubifs has no linebreak at end
	mkfs.ubifs -o ${IMGDEPLOYDIR}/${IMAGE_NAME}${IMAGE_OVERLAY_NAME_SUFFIX}.ubifs ${MKUBIFS_ARGS_OVERLAY}
	bbplain "Created empty UBIFS overlay image"
}

#
# Create symlink to the newly created overlay image
#
python create_symlinks_append() {
    type = "ubifs"
    imgsuffix = d.getVarFlag("do_" + taskname, 'imgsuffix', True) or d.expand("${IMAGE_OVERLAY_NAME_SUFFIX}.")
    dst = os.path.join(deploy_dir, link_name + d.expand("${IMAGE_OVERLAY_NAME_SUFFIX}") + "." + type)
    src = img_name + imgsuffix + type
    if os.path.exists(os.path.join(deploy_dir, src)):
        bb.note("Creating symlink: %s -> %s" % (dst, src))
        if os.path.islink(dst):
            os.remove(dst)
        os.symlink(src, dst)
    else:
        bb.note("Skipping symlink, source does not exist: %s -> %s" % (dst, src))
}
