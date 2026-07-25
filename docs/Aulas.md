## Aulas

### `POST /api/v1/aulas`

**Body:**

```json
{
  "name": "101",
  "edificioId": 1
}
```

| Campo | Tipo | Requerido | Descripcion |
|-------|------|-----------|-------------|
| `name` | string | Si | Nombre del aula |
| `edificioId` | long | Si | ID del edificio |

**Respuesta 201:**

```json
{
  "name": "101",
  "edificioId": 1,
  "id": 1,
  "edificio": {
    "name": "C-1",
    "centroId": 1,
    "id": 1
  },
  "clases": []
}
```

### `GET /api/v1/aulas`

Lista todas las aulas.

**Query params:** `edificioId`, `name`, `search`, `skip`, `limit`

### `GET /api/v1/aulas/{id}`

Obtiene un aula por ID con su edificio y clases.

### `PUT /api/v1/aulas/{id}`

Actualiza un aula.

### `PATCH /api/v1/aulas/{id}`

Actualiza parcialmente un aula.

### `DELETE /api/v1/aulas/{id}`

Elimina un aula. **Respuesta 204.**
