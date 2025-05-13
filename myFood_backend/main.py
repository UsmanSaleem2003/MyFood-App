from fastapi import FastAPI, Depends
from sqlalchemy.orm import Session
from typing import List

import models, schemas, crud
from database import engine, SessionLocal

from fastapi.staticfiles import StaticFiles

models.Base.metadata.create_all(bind=engine)

app = FastAPI()
app.mount("/static", StaticFiles(directory="static"), name="static")

# Dependency
def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()

@app.get("/products/{category}", response_model=List[schemas.ProductOut])
def read_products(category: str, db: Session = Depends(get_db)):
    print(f"📦 API HIT: Received request for category = {category}")
    return crud.get_products_by_category(db, category)

@app.get("/products")
def read_all_products(db: Session = Depends(get_db)):
    return crud.get_all_products(db)
