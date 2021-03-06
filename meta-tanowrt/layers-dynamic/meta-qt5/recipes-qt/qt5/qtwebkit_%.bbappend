FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PR_append = ".tano0"
EXTRA_OECMAKE_append = " -DCMAKE_BUILD_TYPE=Release "

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}/patches:${THISDIR}/${PN}/files:"

# Disable OpenGL
OECMAKE_CXX_FLAGS_append = " -DQT_NO_OPENGL "

SRC_URI += "\
	file://0001-Fix-QtTestBrowser-build-without-OpenGL.patch \
"

PACKAGECONFIG = "fontconfig"

# Disable/enable features
EXTRA_OECMAKE_append = "\
	-DENABLE_OPENGL=OFF \
	-DENABLE_WEBKIT2=OFF \
	-DENABLE_INSPECTOR_UI=OFF \
	-DENABLE_SPELLCHECK=OFF \
	-DENABLE_PRINT_SUPPORT=OFF \
	-DENABLE_VIDEO=OFF \
	-DENABLE_API_TESTS=OFF \
	-DENABLE_DEVICE_ORIENTATION=OFF \
	-DENABLE_JIT=ON \
	-DENABLE_TEST_SUPPORT=OFF \
"

# Fix include path
EXTRA_OECMAKE_append = "\
	-DCMAKE_INSTALL_INCLUDEDIR:PATH=include \
"

do_install_append() {
	install -d ${D}${bindir}
	install -m 0755 ${B}/bin/QtTestBrowser ${D}${bindir}/QtTestBrowser
}
