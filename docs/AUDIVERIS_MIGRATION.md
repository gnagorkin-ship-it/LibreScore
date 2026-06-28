# Audiveris migration notes

Audiveris packages are classified for Android migration as follows: recognition, sheet, score, math and image analysis cores are **KEEP/WRAP**; Swing, JavaFX, desktop dialogs, desktop launchers, printing, clipboard and platform filesystem packages are **REMOVE**; Ghostscript/PDF and OCR integrations are **REWRITE** using Android PdfRenderer and Android-compatible OCR-free preprocessing. CLI entry points are **WRAP** behind `RecognitionEngine`.
