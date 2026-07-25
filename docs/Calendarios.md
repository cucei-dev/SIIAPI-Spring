## Calendarios

### `POST /api/v2/calendarios`

Crea un calendario academico.

**Body:**

```json
{
  "name": "2025-1",
  "siiauId": "20251"
}
```

| Campo | Tipo | Requerido | Descripcion |
|-------|------|-----------|-------------|
| `name` | string | Si | Nombre del calendario |
| `siiauId` | string | Si | ID en SIIAU (unico) |

**Respuesta 201:**

```json
{
  "name": "2025-1",
  "siiauId": "20251",
  "id": 1,
  "secciones": []
}
```

### `GET /api/v2/calendarios`

Lista todos los calendarios.

**Query params:** `skip`, `limit`

**Respuesta:**

```json
{
  "total": 5,
  "results": [
    {
      "name": "2025-1",
      "siiauId": "20251",
      "id": 1,
      "secciones": []
    }
  ]
}
```

### `GET /api/v2/calendarios/{id}`

Obtiene un calendario por ID con sus secciones.

**Respuesta:**

```json
{
  "name": "2025-1",
  "siiauId": "20251",
  "id": 1,
  "secciones": [
    {
      "name": "001",
      "nrc": "12345",
      "cupos": 40,
      "cuposDisponibles": 15,
      "est": "EST",
      "periodoInicio": "2025-08-12T00:00:00",
      "periodoFin": "2025-12-19T23:59:00",
      "centroId": 1,
      "materiaId": 1,
      "profesorId": 1,
      "calendarioId": 1,
      "id": 1
    }
  ]
}
```

### `PUT /api/v2/calendarios/{id}`

Actualiza un calendario completo.

**Body:** Mismo que POST.

### `PATCH /api/v2/calendarios/{id}`

Actualiza parcialmente un calendario.

**Body:** Solo los campos a actualizar.

### `DELETE /api/v2/calendarios/{id}`

Elimina un calendario. **Respuesta 204 sin body.**
