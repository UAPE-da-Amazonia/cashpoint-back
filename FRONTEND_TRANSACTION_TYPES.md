# TransactionTypes - Código Frontend Atualizado

```typescript
async getTransactionTypes(): Promise<{ id: number; name: string }[]> {
  const response = await this.request<TransactionTypeResponse>(
    '/api/transaction-types',
  );
  return response.transactionTypes;
}

async getTransactionType(id: number): Promise<{ id: number; name: string }> {
  const response = await this.request<TransactionTypeResponse>(
    `/api/transaction-types/${id}`,
  );
  return response.transactionTypes[0];
}

async createTransactionType(
  transactionTypeData: { name: string },
): Promise<{ id: number; name: string }> {
  const response = await this.request<TransactionTypeResponse>('/api/transaction-types', {
    method: 'POST',
    body: JSON.stringify(transactionTypeData),
  });
  return response.transactionTypes[0];
}

async updateTransactionType(
  id: number,
  transactionTypeData: { name: string },
): Promise<{ id: number; name: string }> {
  const response = await this.request<TransactionTypeResponse>(`/api/transaction-types/${id}`, {
    method: 'PUT',
    body: JSON.stringify(transactionTypeData),
  });
  return response.transactionTypes[0];
}

async deleteTransactionType(id: number): Promise<void> {
  await this.request(`/api/transaction-types/${id}`, {
    method: 'DELETE',
  });
}
```

## Tipos TypeScript

```typescript
interface TransactionTypeResponse {
  transactionTypes: { id: number; name: string }[];
}
```

## Exemplos de Uso

```typescript
// GET All
const types = await api.getTransactionTypes();
console.log(types); // [{ id: 1, name: "Income" }, { id: 2, name: "Expense" }]

// GET by ID
const type = await api.getTransactionType(1);
console.log(type); // { id: 1, name: "Income" }

// CREATE
const newType = await api.createTransactionType({ name: "Transfer" });
console.log(newType); // { id: 3, name: "Transfer" }

// UPDATE
const updatedType = await api.updateTransactionType(1, { name: "Revenue" });
console.log(updatedType); // { id: 1, name: "Revenue" }

// DELETE
await api.deleteTransactionType(3);
``` 