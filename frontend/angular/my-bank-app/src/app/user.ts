export interface User {
    userId: number;
    username: string;
    password: string;
    email: string;
    phoneNumber: string;
    firstName: string;
    lastName: string;
    dateOfBirth: Date | null; // Use Date for date fields
    address: string;
    otp: string | null; // Nullable fields
    customerId: string;
    createdAt: Date | null; // Use Date for datetime fields
    updatedAt: Date | null;
    profileUpdated: string; // "Y" or "N"
    status: 'ACTIVE' | 'INACTIVE' | 'SUSPENDED'; // Use a union type for enums
    role: 'ADMIN' | 'USER' | 'MODERATOR'; // Use a union type for roles
  }
  