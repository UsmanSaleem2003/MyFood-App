from sqlalchemy.orm import Session
from models import Product, Review
from schemas import ReviewCreate

def get_products_by_category(db: Session, category: str):
    return db.query(Product).filter(Product.category == category).all()

def get_all_products(db: Session):
    return db.query(Product).all()

def get_product_by_id(db: Session, product_id: int):
    return db.query(Product).filter(Product.id == product_id).first()

def add_review(db: Session, product_id: int, review_data: ReviewCreate):
    review = Review(product_id=product_id, text=review_data.text)
    db.add(review)
    db.commit()
    db.refresh(review)
    return review
