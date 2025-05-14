from sqlalchemy import Column, Integer, String, Float, ForeignKey
from sqlalchemy.orm import relationship
from sqlalchemy.ext.declarative import declarative_base

Base = declarative_base()

class Product(Base):
    __tablename__ = "products"

    id = Column(Integer, primary_key=True, index=True)
    name = Column(String)
    category = Column(String)
    image_name = Column(String)
    price = Column(Float)
    rating = Column(Float)
    timing = Column(String)
    description = Column(String)
    ingredients = Column(String)

    reviews = relationship("Review", back_populates="product", cascade="all, delete-orphan")

class Review(Base):
    __tablename__ = "reviews"

    id = Column(Integer, primary_key=True, index=True)
    product_id = Column(Integer, ForeignKey("products.id"))
    text = Column(String)

    product = relationship("Product", back_populates="reviews")
