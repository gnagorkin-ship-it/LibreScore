package org.librescore.engine
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.librescore.common.ScoreBitmap
class HeuristicRecognitionEngineTest { @Test fun detectsFiveStaffLines(){ val w=100; val h=80; val p=IntArray(w*h){0xFFFFFFFF.toInt()}; listOf(10,20,30,40,50).forEach{y-> for(x in 0 until w) p[y*w+x]=0xFF000000.toInt()}; assertEquals(5, HeuristicRecognitionEngine().detectStaffLines(ScoreBitmap(w,h,p)).size) } }
