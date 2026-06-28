# LibreScore

LibreScore is an offline-first Android Optical Music Recognition application architecture inspired by Audiveris. It imports sheet-music PDFs, renders pages locally, recognizes staff structures, emits MusicXML / compressed MXL / MIDI, and provides a Compose UI shell for recognition, playback and export workflows.

## Build locally

```bash
gradle test assembleDebug
```

## Build on GitHub from a phone or tablet

Fork the repository, edit files in the GitHub web UI, commit, and wait for **Actions → Build APKs**. Debug and release APK artifacts are uploaded for every push. Tags named `v*` publish GitHub Releases with APK, AAB and SHA256 checksums.

## Signing

Release signing is optional. Add `KEYSTORE_BASE64`, `KEYSTORE_PASSWORD`, `KEY_ALIAS`, and `KEY_PASSWORD` repository secrets to sign release builds. If secrets are absent, CI still publishes unsigned release outputs.
