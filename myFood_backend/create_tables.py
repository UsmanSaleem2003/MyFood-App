from sqlalchemy import create_engine
from models import Base

DATABASE_URL = "postgresql://postgres:123@localhost:5432/myFood_SMD"

engine = create_engine(DATABASE_URL)
Base.metadata.create_all(bind=engine)

print("✅ Tables created successfully.")
