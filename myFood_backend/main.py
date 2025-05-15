from fastapi import FastAPI, Depends, HTTPException
from sqlalchemy.orm import Session
from typing import List
import models, schemas, crud
from database import engine, SessionLocal
from fastapi.staticfiles import StaticFiles
import logging

# Configure logging
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

models.Base.metadata.create_all(bind=engine)

app = FastAPI()
app.mount("/static", StaticFiles(directory="static"), name="static")

def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()

@app.get("/products", response_model=List[schemas.ProductOut])
def read_all_products(db: Session = Depends(get_db)):
    logger.info("GET /products - fetching all products")
    return crud.get_all_products(db)

@app.get("/products/{category}", response_model=List[schemas.ProductOut])
def read_products_by_category(category: str, db: Session = Depends(get_db)):
    logger.info(f"GET /products/{category} - category requested")
    return crud.get_products_by_category(db, category)

@app.get("/product/{product_id}", response_model=schemas.ProductOut)
def read_single_product(product_id: int, db: Session = Depends(get_db)):
    logger.info(f"GET /product/{product_id} - product ID requested")
    product = crud.get_product_by_id(db, product_id)
    if not product:
        raise HTTPException(status_code=404, detail="Product not found")
    return product

@app.post("/product/{product_id}/review", response_model=schemas.ReviewOut)
def create_review(product_id: int, review: schemas.ReviewCreate, db: Session = Depends(get_db)):
    logger.info(f"POST /product/{product_id}/review - review submitted")
    return crud.add_review(db, product_id, review)

@app.get("/search", response_model=List[schemas.ProductOut])
def search_products(q: str, db: Session = Depends(get_db)):
    logger.info(f"GET /search?q={q} - search initiated")
    return crud.search_products(db, q)
