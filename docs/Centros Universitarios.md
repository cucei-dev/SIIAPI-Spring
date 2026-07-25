## Centros Universitarios

### `POST /api/v1/centros`

**Body:**

```json
{
  "name": "CUCEI",
  "siiauId": "CUCEI"
}
```

| Campo | Tipo | Requerido | Descripcion |
|-------|------|-----------|-------------|
| `name` | string | Si | Nombre del centro |
| `siiauId` | string | Si | ID en SIIAU (unico) |

**Respuesta 201:**

```json
{
  "name": "CUCEI",
  "siiauId": "CUCEI",
  "id": 1,
  "secciones": [],
  "edificios": []
}
```

### `GET /api/v1/centros`

Lista todos los centros.

**Query params:** `search`, `skip`, `limit`

### `GET /api/v1/centros/{id}`

Obtiene un centro por ID con secciones y edificios.

### `PUT /api/v1/centros/{id}`

Actualiza un centro completo.

### `PATCH /api/v1/centros/{id}`

Actualiza parcialmente un centro.

### `DELETE /api/v1/centros/{id}`

Elimina un centro. **Respuesta 204.**
