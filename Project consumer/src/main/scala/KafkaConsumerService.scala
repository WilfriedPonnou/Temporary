import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.{SparkConf, SparkContext, sql}
import org.apache.spark.sql.{SQLContext, SparkSession}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe

object KafkaConsumerService extends App{


  val conf = new SparkConf().setMaster("local[2]").setAppName("KafkaConsumer")
  val ssc = new StreamingContext(conf, Seconds(1))
  val kafkaParams= Map[String,Object](
    "bootstrap.servers"-> "localhost:9092",
    "key.deserializer" -> classOf[StringDeserializer],
    "value.deserializer" -> classOf[StringDeserializer],
    "group.id" -> "myconsumergroup",
    "auto.offset.reset" -> "latest",
    "enable.auto.commit" -> (false: java.lang.Boolean)

  )
  
  val topics = Array("peaceWatcherReport")
  val stream = KafkaUtils.createDirectStream[String,String](
    ssc,
    PreferConsistent,
    Subscribe[String,String](topics,kafkaParams)
  ).map{record => (record.key(), record.value)}

}
