import java.lang.StringBuilder

with(StringBuilder()){
        for(a in 1..10){
            for(b in 1..10){
                println("{\"a\":\"$a * $b\",\"b\":\"${a*b}\",\"c\":0},")
            }
        }
}
