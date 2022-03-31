import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import java.util
import java.util.Properties
import java.time.Duration
import java.io.File
import scala.collection.JavaConverters._
import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.streaming.StreamingContext._
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe


object KafkaConsumerService extends App{

  /*val props:Properties = new Properties()
  props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
  props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer])
  props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer])
  props.put(ConsumerConfig.GROUP_ID_CONFIG, "myconsumergroup")

  val consumer = new KafkaConsumer[String, String](props)
  consumer.subscribe(List("peaceWatcherReport").asJava)
  val records: ConsumerRecords[String,String]= consumer.poll(Duration.ofMillis(100))

  while(true){
    val records: ConsumerRecords[String,String]= consumer.poll(Duration.ofMillis(100))
    for (record<-records.asScala){
     println(record)
    }
  }*/
  val conf = new SparkConf().setMaster("local[2]").setAppName("KafkaConsumer")
  val ssc = new StreamingContext(sparkConf, Seconds(1))
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
    Suscribe[String,String](topics,kafkaParams)
  ).map{record => (record.key(), record.value)}.foreach{println}
/*
  val topic = "peaceWatcherReport"
  val bootstrapServer = "localhost:9092"
  val namenode= "hdfs://localhost:8020"

  val df: DataFrame = spark
    .readStream
    .format(source="kafka")
    .option("kafka.boostrap.servers", bootstrapServer)
    .option("suscribe", topic)
    .option("startingOffsets", "earliest")
    .load()*/

}