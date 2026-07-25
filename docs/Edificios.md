## Edificios

### `POST /api/v1/edificios`

**Body:**

```json
{
  "name": "C-1",
  "centroId": 1
}
```

| Campo | Tipo | Requerido | Descripcion |
|-------|------|-----------|-------------|
| `name` | string | Si | Nombre del edificio |
| `centroId` | long | Si | ID del centro universitario |

**Respuesta 201:**

```json
{
  "name": "C-1",
  "centroId": 1,
  "id": 1,
  "centro": {
    "name": "CUCEI",
    "siiauId": "CUCEI",
    "id": 1
  },
  "aulas": []
}
```

### `GET /api/v1/edificios`

Lista todos los edificios.

**Query params:** `centroId`, `name`, `search`, `skip`, `limit`

### `GET /api/v1/edificios/{id}`

Obtiene un edificio por ID con su centro y aulas.

### `PUT /api/v1/edificios/{id}`

Actualiza un edificio.

### `PATCH /api/v1/edificios/{id}`

Actualiza parcialmente un edificio.

### `DELETE /api/v1/edificios/{id}`

Elimina un edificio. **Respuesta 204.**
