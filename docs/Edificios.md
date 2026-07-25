## Edificios

### `POST /api/v2/edificios`

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

### `GET /api/v2/edificios`

Lista todos los edificios.

**Query params:** `centroId`, `name`, `search`, `skip`, `limit`

### `GET /api/v2/edificios/{id}`

Obtiene un edificio por ID con su centro y aulas.

### `PUT /api/v2/edificios/{id}`

Actualiza un edificio.

### `PATCH /api/v2/edificios/{id}`

Actualiza parcialmente un edificio.

### `DELETE /api/v2/edificios/{id}`

Elimina un edificio. **Respuesta 204.**
