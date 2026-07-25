## Profesores

### `POST /api/v1/profesores`

**Body:**

```json
{
  "name": "Juan Perez Garcia"
}
```

| Campo | Tipo | Requerido | Descripcion |
|-------|------|-----------|-------------|
| `name` | string | Si | Nombre completo (unico) |

**Respuesta 201:**

```json
{
  "name": "Juan Perez Garcia",
  "id": 1,
  "secciones": []
}
```

### `GET /api/v1/profesores`

Lista todos los profesores.

**Query params:** `name`, `search`, `skip`, `limit`

### `GET /api/v1/profesores/{id}`

Obtiene un profesor por ID con sus secciones.

### `PUT /api/v1/profesores/{id}`

Actualiza un profesor.

### `PATCH /api/v1/profesores/{id}`

Actualiza parcialmente un profesor.

### `DELETE /api/v1/profesores/{id}`

Elimina un profesor. **Respuesta 204.**
