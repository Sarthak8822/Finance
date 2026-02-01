# Finance Tracker - Microservices Application 💰

## Kya hai ye application?

Ye ek complete **Finance Tracker Application** hai jo **Microservices Architecture** pe based hai. Isme tum apne:
- Income/Expense track kar sakte ho
- Budget set kar sakte ho
- Reports dekh sakte ho
- User authentication kar sakte ho

## Microservices Architecture kya hota hai? 🏗️

**Simple explanation:**
- Ek bada application ko chote-chote independent services mein tod dete hain
- Har service apna specific kaam karti hai
- Sab services ek dusre se independently run hoti hain
- Agar ek service down ho jaye, to baki services chal sakti hain

**Example:** Swiggy/Zomato mein:
- User Service → User details manage karta hai
- Restaurant Service → Restaurant data manage karta hai
- Order Service → Orders handle karta hai
- Payment Service → Payments handle karta hai

## Hamare Application mein kitne Microservices hain? 📦

### 1. **Eureka Service (Service Registry)** - Port 8761
- **Kaam:** Sabhi microservices ko register aur track karta hai
- **Analogy:** School mein ek register hota hai jaha sab students ki attendance hoti hai
- **Technology:** Spring Cloud Netflix Eureka

### 2. **API Gateway** - Port 8080
- **Kaam:** Single entry point for all requests (like a main gate)
- **Analogy:** Shopping mall ka main entrance - andar jaane ke liye sabko yahi se jaana padta hai
- **Features:** Routing, Load Balancing, Authentication check

### 3. **User Service** - Port 8081
- **Kaam:** User registration, login, profile management
- **Database:** PostgreSQL (user_db)
- **Endpoints:**
    - POST /api/users/register
    - POST /api/users/login
    - GET /api/users/profile

### 4. **Transaction Service** - Port 8082
- **Kaam:** Income aur Expense transactions manage karta hai
- **Database:** PostgreSQL (transaction_db)
- **Endpoints:**
    - POST /api/transactions (Add transaction)
    - GET /api/transactions (Get all transactions)
    - GET /api/transactions/summary (Get summary)

### 5. **Budget Service** - Port 8083
- **Kaam:** Budget set karna aur track karna
- **Database:** PostgreSQL (budget_db)
- **Endpoints:**
    - POST /api/budgets (Create budget)
    - GET /api/budgets (Get all budgets)
    - GET /api/budgets/alerts (Check budget alerts)

### 6. **Report Service** - Port 8084
- **Kaam:** Financial reports generate karta hai
- **Features:** Monthly/Yearly reports, Charts data
- **Endpoints:**
    - GET /api/reports/monthly
    - GET /api/reports/yearly
    - GET /api/reports/category-wise

## Architecture Diagram 🎨

```
                          ┌─────────────────┐
                          │   API Gateway   │
                          │   (Port 8080)   │
                          └────────┬────────┘
                                   │
                    ┌──────────────┼──────────────┐
                    │              │              │
         ┌──────────▼────┐  ┌─────▼──────┐  ┌───▼────────┐
         │ User Service  │  │Transaction │  │   Budget   │
         │  (Port 8081)  │  │  Service   │  │  Service   │
         └───────────────┘  │(Port 8082) │  │(Port 8083) │
                            └────────────┘  └────────────┘
                                   │
                            ┌──────▼─────────┐
                            │ Report Service │
                            │  (Port 8084)   │
                            └────────────────┘
                                   │
                    ┌──────────────┼──────────────┐
                    │              │              │
         ┌──────────▼────┐  ┌─────▼──────┐  ┌───▼────────┐
         │   user_db     │  │transaction │  │ budget_db  │
         │  (Postgres)   │  │    _db     │  │ (Postgres) │
         └───────────────┘  └────────────┘  └────────────┘
                                   
                    ┌───────────────────────┐
                    │  Eureka Server        │
                    │  (Service Registry)   │
                    │    (Port 8761)        │
                    └───────────────────────┘
```

## Microservices ke beech Communication kaise hota hai? 🔄

### 1. **Service Discovery (Eureka)**
```
User Service start hoti hai
    ↓
Eureka Server pe register hoti hai
    ↓
API Gateway Eureka se pucha: "User Service kaha hai?"
    ↓
Eureka: "Port 8081 pe hai"
    ↓
API Gateway request forward kar deta hai
```

### 2. **Inter-Service Communication**
- **REST API calls** - Services ek dusre ko HTTP requests bhejti hain
- **Example:** Report Service → Transaction Service se data mangti hai

## Technologies Used 🛠️

1. **Spring Boot** - Microservices banane ke liye
2. **Spring Cloud** - Microservices features (Eureka, Gateway, etc.)
3. **PostgreSQL** - Database
4. **Maven** - Build tool
5. **JWT** - Authentication
6. **Lombok** - Boilerplate code reduce karne ke liye

## Project Structure 📁

```
finance-tracker-microservices/
│
├── eureka-service/           # Service Registry
├── api-gateway/              # API Gateway
├── user-service/             # User Management
├── transaction-service/      # Transactions
├── budget-service/           # Budget Management
├── report-service/           # Reports Generation
└── docker-compose.yml        # All services ko ek saath run karne ke liye
```

## How to Run? 🚀

### Prerequisites:
- Java 17+
- Maven
- PostgreSQL (ya Docker)

### Steps:

1. **Clone the project**
```bash
cd finance-tracker-microservices
```

2. **Start Eureka Server**
```bash
cd eureka-service
mvn spring-boot:run
```

3. **Start API Gateway**
```bash
cd api-gateway
mvn spring-boot:run
```

4. **Start all other services**
```bash
# User Service
cd user-service
mvn spring-boot:run

# Transaction Service
cd transaction-service
mvn spring-boot:run

# Budget Service
cd budget-service
mvn spring-boot:run

# Report Service
cd report-service
mvn spring-boot:run
```

5. **Access Eureka Dashboard**
```
http://localhost:8761
```

6. **Test API Gateway**
```
http://localhost:8080/api/users/...
```

## Testing the Application 🧪

### 1. Register User
```bash
curl -X POST http://localhost:8080/api/users/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john",
    "email": "john@example.com",
    "password": "password123"
  }'
```

### 2. Add Transaction
```bash
curl -X POST http://localhost:8080/api/transactions \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <your-jwt-token>" \
  -d '{
    "amount": 5000,
    "type": "INCOME",
    "category": "Salary",
    "description": "Monthly salary"
  }'
```

### 3. Create Budget
```bash
curl -X POST http://localhost:8080/api/budgets \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <your-jwt-token>" \
  -d '{
    "category": "Food",
    "amount": 10000,
    "month": "JANUARY"
  }'
```

## Key Concepts Explained 📚

### 1. **Service Registry (Eureka)**
- Jaise phone directory hoti hai waise hi
- Har service apni location register karti hai
- Dusri services yaha se address dhoond sakti hain

### 2. **API Gateway**
- Single point of entry
- Security check karta hai
- Requests ko sahi service tak pahunchata hai

### 3. **Load Balancing**
- Agar ek service ki 3 instances chal rahi hain
- Gateway load ko distribute kar deta hai

### 4. **Database per Service**
- Har service ka apna database hota hai
- Isse loose coupling milti hai
- Ek database ka issue dusri service ko affect nahi karta

## Advantages of Microservices ✅

1. **Scalability** - Sirf busy service ko scale kar sakte ho
2. **Independent Deployment** - Ek service update karo, baki chal rahi hain
3. **Technology Flexibility** - Har service different tech use kar sakti hai
4. **Fault Isolation** - Ek service fail ho to baki safe hain
5. **Easy to Understand** - Choti services samajhna easy hai

## Common Issues & Solutions 🔧

### Issue 1: Service Eureka pe register nahi ho rahi
**Solution:** Check if Eureka server is running on 8761

### Issue 2: Database connection error
**Solution:** Verify PostgreSQL credentials in application.yml

### Issue 3: Port already in use
**Solution:** Change port in application.yml or kill the process

## Next Steps 🎯

1. Add **Config Server** for centralized configuration
2. Implement **Circuit Breaker** (Resilience4j)
3. Add **Distributed Tracing** (Zipkin/Sleuth)
4. Implement **Message Queue** (RabbitMQ/Kafka)
5. Add **Caching** (Redis)

## Learning Resources 📖

1. Spring Boot Documentation
2. Spring Cloud Documentation
3. Microservices.io
4. YouTube: "Spring Boot Microservices" tutorials

---

**Happy Learning! 🚀**#   F i n a n c e  
 