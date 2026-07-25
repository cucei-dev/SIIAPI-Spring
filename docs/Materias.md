## Materias

### `POST /api/v1/materias`

**Body:**

```json
{
  "name": "Programacion Orientada a Objetos",
  "creditos": 8,
  "clave": "TC1024"
}
```

| Campo | Tipo | Requerido | Descripcion |
|-------|------|-----------|-------------|
| `name` | string | Si | Nombre de la materia |
| `creditos` | int | Si | Creditos (>= 0) |
| `clave` | string | Si | Clave unica (ej: "TC1024") |

**Respuesta 201:**

```json
{
  "name": "Programacion Orientada a Objetos",
  "creditos": 8,
  "clave": "TC1024",
  "id": 1,
  "secciones": []
}
```

### `GET /api/v1/materias`

Lista todas las materias.

**Query params:** `clave`, `search`, `skip`, `limit`

### `GET /api/v1/materias/{id}`

Obtiene una materia por ID con sus secciones.

### `PUT /api/v1/materias/{id}`

Actualiza una materia completa.

### `PATCH /api/v1/materias/{id}`

Actualiza parcialmente una materia.

### `DELETE /api/v1/materias/{id}`

Elimina una materia. **Respuesta 204.**
