import json
from sqlalchemy.orm import Session
from models import Product
from database import SessionLocal

with open("products.json", "r") as f:
    products_data = json.load(f)

db: Session = SessionLocal()

for product in products_data["products"]:
    new_product = Product(
        name=product["name"],
        category=product["category"],
        price=product["price"],
        rating=product["rating"],
        image_name=product["imageName"],
        timing=product["availability_time"],
        description=product["description"],
        ingredients=product["ingredients"]
    )
    db.add(new_product)

db.commit()
db.close()

print("Products loaded successfully!")
