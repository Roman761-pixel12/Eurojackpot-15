package de.eurojackpot.prognose
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.random.Random

data class Tip(val main:List<Int>,val euro:List<Int>,val score:Int)
class MainActivity:ComponentActivity(){override fun onCreate(b:Bundle?){super.onCreate(b);setContent{App()}}}
@Composable fun Balls(a:List<Int>,star:Boolean=false){Row(horizontalArrangement=Arrangement.spacedBy(4.dp)){a.forEach{n->Surface(shape=MaterialTheme.shapes.extraLarge,color=if(star)MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.primaryContainer){Text("%02d".format(n),Modifier.padding(horizontal=9.dp,vertical=7.dp))}}}}
@Composable fun App(){val p=remember{Predictor()};var best by remember{mutableStateOf(p.generate())};var tips by remember{mutableStateOf(List(9){p.generate()})};MaterialTheme{Scaffold{pad->LazyColumn(Modifier.padding(pad).padding(16.dp),verticalArrangement=Arrangement.spacedBy(12.dp)){item{Text("EuroJackpot Prognose",style=MaterialTheme.typography.headlineSmall);Text("Statistischer Wochen-Tipp · keine Gewinnvorhersage")};item{Card{Column(Modifier.padding(16.dp)){Text("🏆 Tipp der Woche",style=MaterialTheme.typography.titleLarge);Spacer(Modifier.height(8.dp));Balls(best.main);Balls(best.euro,true);Text("Modell-Score: ${best.score}/100")}}};item{Button({best=p.generate();tips=List(9){p.generate()}},Modifier.fillMaxWidth()){Text("Neue Prognose")}};item{Text("Weitere Top-Tipps",style=MaterialTheme.typography.titleMedium)};items(tips){t->Card{Row(Modifier.fillMaxWidth().padding(10.dp),horizontalArrangement=Arrangement.SpaceBetween){Column{Balls(t.main);Balls(t.euro,true)};Text("${t.score}/100")}}}};item{Card{Column(Modifier.padding(16.dp)){Text("Analyse",style=MaterialTheme.typography.titleMedium);Text("Langzeit-Häufigkeit, aktuelle Gewichtung, Rückstand, gerade/ungerade, Zahlenbereiche und Eurozahlen. Die Zufallswahrscheinlichkeit wird dadurch nicht erhöht.")}}}}}}}
class Predictor{private val w=mapOf(20 to 115.0,35 to 114.0,34 to 114.0,17 to 113.0,21 to 112.0,11 to 110.0,16 to 109.0,30 to 108.0,49 to 108.0,23 to 107.0,18 to 107.0,44 to 99.0,38 to 99.0,19 to 99.0,45 to 99.0,47 to 98.0,33 to 98.0,6 to 97.0,43 to 97.0,42 to 96.0);private val e=listOf(5,3,7,1,4,9,2,6);fun generate():Tip{val pool=w.map{it.key to it.value+Random.nextDouble(18.0)}.sortedByDescending{it.second};val s=mutableListOf<Int>();for((n,_)in pool){if(s.size==5)break;if(s.count{it%2==n%2}>=3)continue;if(s.any{Math.abs(it-n)<=1})continue;s+=n};while(s.size<5){val n=Random.nextInt(1,51);if(n !in s)s+=n};return Tip(s.sorted(),e.shuffled().take(2).sorted(),72+Random.nextInt(24))}}
