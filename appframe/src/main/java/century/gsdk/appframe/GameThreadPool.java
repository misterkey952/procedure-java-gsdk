package century.gsdk.appframe;

import org.dom4j.Element;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
/**
 *     Copyright (C) <2019>  <Century>
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 *     Author Email:   misterkey952@gmail.com		280202806@qq.com	yjy116@163.com.
 */
public abstract class GameThreadPool {

    private Executor executor;
    protected GameThreadFactory factory;

    public void init(){
        factory=new GameThreadFactory(this.getClass().getSimpleName());
        executor=initializeExecutor();
    }

    protected abstract Executor initializeExecutor();

    public void execute(Runnable run){
        try {
            executor.execute(run);
        } catch (Exception e) {
            AppFrameLogger.GameThreadPool.error("execute err",e);
        }
    }

    public void shutdown() {
        ((ExecutorService) executor).shutdown();
    }
}
