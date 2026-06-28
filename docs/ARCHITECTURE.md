# Architecture

Modules are intentionally small and interface-oriented: `app` owns Compose UI, `pdf` streams Android PdfRenderer pages, `engine` owns OMR pipeline contracts, `musicxml` serializes MusicXML/MXL, `midi` writes Standard MIDI Files, `player` wraps local MIDI playback, `storage` handles SAF/share export, and `common` contains portable models.
