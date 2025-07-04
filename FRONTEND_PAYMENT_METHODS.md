# PaymentMethods - Código Frontend

```typescript
async getPaymentMethods(businessUnitId?: number): Promise<PaymentMethod[]> {
  const params = businessUnitId ? `?businessUnitId=${businessUnitId}` : '';
  const response = await this.request<PaymentMethodResponse>(`/api/payment-methods${params}`);
  return response.paymentMethods;
}

async getPaymentMethod(id: number): Promise<PaymentMethod> {
  const response = await this.request<PaymentMethodResponse>(`/api/payment-methods/${id}`);
  return response.paymentMethods[0];
}

async createPaymentMethod(
  paymentMethodData: {
    name: string;
    description: string;
    businessUnitId?: number;
  },
): Promise<PaymentMethod> {
  const response = await this.request<PaymentMethodResponse>('/api/payment-methods', {
    method: 'POST',
    body: JSON.stringify(paymentMethodData),
  });
  return response.paymentMethods[0];
}

async updatePaymentMethod(
  id: number,
  paymentMethodData: {
    name: string;
    description: string;
    businessUnitId?: number;
  },
): Promise<PaymentMethod> {
  const response = await this.request<PaymentMethodResponse>(`/api/payment-methods/${id}`, {
    method: 'PUT',
    body: JSON.stringify(paymentMethodData),
  });
  return response.paymentMethods[0];
}

async deletePaymentMethod(id: number): Promise<void> {
  await this.request(`/api/payment-methods/${id}`, {
    method: 'DELETE',
  });
}

async activatePaymentMethod(id: number): Promise<PaymentMethod> {
  const response = await this.request<PaymentMethodResponse>(`/api/payment-methods/${id}/activate`, {
    method: 'PUT',
  });
  return response.paymentMethods[0];
}

async deactivatePaymentMethod(id: number): Promise<PaymentMethod> {
  const response = await this.request<PaymentMethodResponse>(`/api/payment-methods/${id}/deactivate`, {
    method: 'PUT',
  });
  return response.paymentMethods[0];
}
```

## Tipos TypeScript

```typescript
interface PaymentMethod {
  id: number;
  name: string;
  description: string;
  businessUnitId: number;
  isActive: boolean;
}

interface PaymentMethodResponse {
  paymentMethods: PaymentMethod[];
}
```

## Exemplos de Uso

```typescript
// GET All
const methods = await api.getPaymentMethods(1);
console.log(methods); // [{ id: 1, name: "PIX", description: "...", businessUnitId: 1, isActive: true }]

// GET by ID
const method = await api.getPaymentMethod(1);
console.log(method); // { id: 1, name: "PIX", description: "...", businessUnitId: 1, isActive: true }

// CREATE
const newMethod = await api.createPaymentMethod({
  name: "Cartão de Crédito",
  description: "Pagamento com cartão de crédito",
  businessUnitId: 1
});
console.log(newMethod); // { id: 6, name: "Cartão de Crédito", ... }

// UPDATE
const updatedMethod = await api.updatePaymentMethod(1, {
  name: "PIX Atualizado",
  description: "Descrição atualizada",
  businessUnitId: 1
});
console.log(updatedMethod); // { id: 1, name: "PIX Atualizado", ... }

// DELETE
await api.deletePaymentMethod(6);

// ACTIVATE/DEACTIVATE
const activated = await api.activatePaymentMethod(1);
const deactivated = await api.deactivatePaymentMethod(1);
``` 