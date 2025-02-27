/** Project:Lab3Mistry3
 * Purpose Details:
 * Course:IST242
 * Author:Ishaben Mistry
 * Date Developed:02/27/25
 * Last Date Changed:
 * Rev:1

 */
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.FindIterable;
import org.bson.Document;

public class MongoDB {
    public static void main(String[] args) {
        // Create a MongoClient using the factory method
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            // Access the database and collection
            MongoDatabase database = mongoClient.getDatabase("retailstore");
            MongoCollection<Document> collection = database.getCollection("Customers");

            // Example: Insert a document
            Document newCustomer1 = new Document("first_name", "John")
                    .append("last_name", "Doe")
                    .append("age", 21)
                    .append("email", "john@example.com");
            collection.insertOne(newCustomer1);

            Document newCustomer2 = new Document("first_name", "Jim")
                    .append("last_name", "Doe")
                    .append("age", 22)
                    .append("email", "jim@example.com");
            collection.insertOne(newCustomer2);

            Document newCustomer3 = new Document("first_name", "Joe")
                    .append("last_name", "Doe")
                    .append("age", 23)
                    .append("email", "joe@example.com");
            collection.insertOne(newCustomer3);

            // Read
            FindIterable<Document> customers = collection.find();
            for (Document customer : customers) {
                System.out.println(customer.toJson());
            }

            // Update
            Document updatedCustomer = new Document("$set", new Document("first_name", "Updated First Name"));
            collection.updateOne(new Document("first_name", "John"), updatedCustomer);

            // Read again
            customers = collection.find();
            for (Document customer : customers) {
                System.out.println(customer.toJson());
            }

            // Delete
            collection.deleteOne(new Document("first_name", "John"));

        }
    }
}

