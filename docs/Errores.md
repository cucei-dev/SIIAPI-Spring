## Errores

Los errores siguen el formato RFC 7807 (Problem Details):

```json
{
  "type": "https://api.cucei.dev/errors/not-found",
  "title": "Not Found",
  "status": 404,
  "detail": "Calendario not found.",
  "instance": "/api/v2/calendarios/999"
}
```

| Status | Tipo | Descripcion |
|--------|------|-------------|
| 404 | `not-found` | El recurso no existe |
| 409 | `conflict` | Conflicto (duplicado) |
| 401 | `unauthorized` | API key faltante o invalida |
| 422 | `validation` | Error de validacion |
