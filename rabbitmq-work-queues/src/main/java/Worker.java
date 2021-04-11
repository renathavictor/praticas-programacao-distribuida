import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class Worker {

  private static final String TASK_QUEUE_NAME = "task_queue";

  public static void main(String[] argv) throws Exception {
    //Criacao de uma factory de conexao, responsavel por criar as conexoes
    ConnectionFactory connectionFactory = new ConnectionFactory();

    //localizacao do gestor da fila (Queue Manager)
    connectionFactory.setHost("localhost");
    connectionFactory.setPort(5672);
    connectionFactory.setUsername("mqadmin");
    connectionFactory.setPassword("Admin123XX_");

    //criacao de uma conexao
    final Connection connection = connectionFactory.newConnection();
    //criando um canal na conexao
    final Channel channel = connection.createChannel();

    channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
    System.out.println(" [*] Aguardando mensagens. Para sair, pressione CTRL + C");

    int prefetchCount =1;
    channel.basicQos(prefetchCount);

    DeliverCallback deliverCallback = (consumerTag, delivery) -> {
      String mensagem = new String(delivery.getBody(), "UTF-8");

      System.out.println(" [x] Recebido '" + mensagem + "'");
      try {
        doWork(mensagem);
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        System.out.println(" [x] Feito");
        channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
      }
    };
    boolean autoAck = false;
    channel.basicConsume(TASK_QUEUE_NAME, autoAck, deliverCallback, consumerTag -> { });
  }

  private static void doWork(String task) throws InterruptedException {
    for (char ch : task.toCharArray()) {
      if (ch == '.') {
        try {
          Thread.sleep(1000);
        } catch (InterruptedException _ignored) {
          Thread.currentThread().interrupt();
        }
      }
    }
  }
}

