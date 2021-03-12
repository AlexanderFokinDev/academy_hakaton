package pt.amn.moveon.workers

import pt.amn.moveon.data.local.AppDatabase
import javax.inject.Inject

class WorkerDependency @Inject constructor(var database: AppDatabase) {
}