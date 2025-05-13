from sqlalchemy.orm import Session
from models import Product

def get_products_by_category(db: Session, category: str):
    # return db.query(Product).filter(Product.category == category).all()
    return db.query(Product).filter(Product.category == category).all()

def get_all_products(db: Session):
    return db.query(Product).all()
