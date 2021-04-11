import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class NewTask {

  private static final String TASK_QUEUE_NAME = "task_queue";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory connectionFactory = new ConnectionFactory();
    connectionFactory.setHost("localhost");
    connectionFactory.setPort(5672);
    connectionFactory.setUsername("mqadmin");
    connectionFactory.setPassword("Admin123XX_");

    try (Connection conexao = connectionFactory.newConnection();
         Channel canal = conexao.createChannel()) {
      boolean duravel = true;
      canal.queueDeclare(TASK_QUEUE_NAME, duravel, false, false, null);

      String message = String.join(" - Renatha do Nascimento Victor - ", argv);

      canal.basicPublish("", TASK_QUEUE_NAME,
              MessageProperties.PERSISTENT_TEXT_PLAIN,
              message.getBytes("UTF-8"));
      System.out.println(" [x] Enviado '" + message + "'");
    }
  }

}