package com.juanchispo.finanzas_componentes.language

enum class AppLanguage { ES, EN }

data class AppStrings(
    val appName: String = "SmartExpense",
    // Auth - Login
    val emailLabel: String, val emailHint: String,
    val passwordLabel: String, val passwordHint: String,
    val signInButton: String, val noAccount: String, val signUpLink: String,
    val invalidCredentials: String,
    // Auth - Register
    val confirmPasswordLabel: String, val confirmPasswordHint: String,
    val fullNameLabel: String, val fullNameHint: String, val choosePasswordHint: String,
    val signUpButton: String, val alreadyAccount: String, val signInLink: String,
    val allRequired: String, val passwordMismatch: String,
    // Home
    val homeTitle: String, val monthlyExpense: String, val budgetLabel: String,
    val recentExpenses: String, val addExpenseFab: String,
    val navHome: String, val navHistory: String, val navBudget: String,
    // History
    val historyTitle: String, val filterButton: String,
    // Add Expense
    val addExpenseTitle: String, val amountLabel: String, val categoryLabel: String,
    val dateLabel: String, val descriptionLabel: String, val saveButton: String,
    val catFood: String, val catTransport: String, val catHealth: String, val catLeisure: String,
    // Budget
    val budgetTitle: String, val monthlyBudget: String, val used: String,
    val remaining: String, val editBudget: String,
    // Profile
    val profileTitle: String, val editNameButton: String, val changeLanguage: String,
    val signOutButton: String, val nameLabel: String, val namePlaceholder: String,
    val saveLabel: String, val cancelLabel: String,
    val spanish: String, val english: String, val selectLanguage: String
)

fun getStrings(lang: AppLanguage): AppStrings = when (lang) {
    AppLanguage.ES -> AppStrings(
        emailLabel = "Correo Electrónico", emailHint = "Ingresa tu correo",
        passwordLabel = "Contraseña", passwordHint = "Ingresa tu contraseña",
        signInButton = "Iniciar Sesión", noAccount = "¿No tienes cuenta? ",
        signUpLink = "Regístrate", invalidCredentials = "Credenciales incorrectas",
        confirmPasswordLabel = "Confirmar Contraseña", confirmPasswordHint = "Confirma tu contraseña",
        fullNameLabel = "Nombre Completo", fullNameHint = "Ingresa tu nombre",
        choosePasswordHint = "Elige una contraseña",
        signUpButton = "Registrarse", alreadyAccount = "¿Ya tienes cuenta? ",
        signInLink = "Inicia Sesión", allRequired = "Todos los campos son obligatorios",
        passwordMismatch = "Las contraseñas no coinciden",
        homeTitle = "Resumen", monthlyExpense = "Gasto del Mes:", budgetLabel = "Presupuesto:",
        recentExpenses = "Últimos Gastos", addExpenseFab = "Añadir Gasto",
        navHome = "Inicio", navHistory = "Historial", navBudget = "Presupuesto",
        historyTitle = "Historial de Gastos", filterButton = "Filtrar",
        addExpenseTitle = "Agregar Gasto", amountLabel = "Monto", categoryLabel = "Categoría",
        dateLabel = "Fecha", descriptionLabel = "Descripción", saveButton = "Guardar",
        catFood = "Comida", catTransport = "Transporte", catHealth = "Salud", catLeisure = "Ocio",
        budgetTitle = "Presupuesto", monthlyBudget = "Presupuesto Mensual",
        used = "Usado", remaining = "Restante:", editBudget = "Editar Presupuesto",
        profileTitle = "Mi Perfil", editNameButton = "Editar Nombre",
        changeLanguage = "Cambiar Idioma", signOutButton = "Cerrar Sesión",
        nameLabel = "Nombre", namePlaceholder = "Ingresa tu nombre",
        saveLabel = "Guardar", cancelLabel = "Cancelar",
        spanish = "Español", english = "Inglés", selectLanguage = "Seleccionar Idioma"
    )
    AppLanguage.EN -> AppStrings(
        emailLabel = "Email", emailHint = "Enter your email",
        passwordLabel = "Password", passwordHint = "Enter your password",
        signInButton = "Sign In", noAccount = "Don't have an account? ",
        signUpLink = "Sign Up", invalidCredentials = "Wrong credentials",
        confirmPasswordLabel = "Confirm Password", confirmPasswordHint = "Confirm your password",
        fullNameLabel = "Full Name", fullNameHint = "Enter your name",
        choosePasswordHint = "Choose a password",
        signUpButton = "Sign Up", alreadyAccount = "Already have an account? ",
        signInLink = "Sign In", allRequired = "All fields are required",
        passwordMismatch = "Passwords do not match",
        homeTitle = "Summary", monthlyExpense = "Monthly Expense:", budgetLabel = "Budget:",
        recentExpenses = "Recent Expenses", addExpenseFab = "Add Expense",
        navHome = "Home", navHistory = "History", navBudget = "Budget",
        historyTitle = "Expense History", filterButton = "Filter",
        addExpenseTitle = "Add Expense", amountLabel = "Amount", categoryLabel = "Category",
        dateLabel = "Date", descriptionLabel = "Description", saveButton = "Save",
        catFood = "Food", catTransport = "Transport", catHealth = "Health", catLeisure = "Leisure",
        budgetTitle = "Budget", monthlyBudget = "Monthly Budget",
        used = "Used", remaining = "Remaining:", editBudget = "Edit Budget",
        profileTitle = "My Profile", editNameButton = "Edit Name",
        changeLanguage = "Change Language", signOutButton = "Sign Out",
        nameLabel = "Name", namePlaceholder = "Enter your name",
        saveLabel = "Save", cancelLabel = "Cancel",
        spanish = "Spanish", english = "English", selectLanguage = "Select Language"
    )
}
